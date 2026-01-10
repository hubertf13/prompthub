"use client";

import { useEffect } from "react";
import { useSession } from "next-auth/react";
import { useRouter, useSearchParams } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

import { schemaPrompt } from "../../../lib/schemas";
import { Form } from "../../../components/Form";

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

const EditPrompt = () => {
    const { data: session, status } = useSession();
    const router = useRouter();
    const searchParams = useSearchParams();
    const postId = searchParams.get("id");

    const form = useForm<z.infer<typeof schemaPrompt>>({
        resolver: zodResolver(schemaPrompt),
        defaultValues: {
            prompt: "",
            tag: "",
        },
    });

    useEffect(() => {
        if (status === "unauthenticated") {
            router.push("/login");
            return;
        }

        const getPostDetails = async () => {
            if (!postId || !session?.user?.token) return;

            try {
                const response = await fetch(`${baseUrl}/api/v1/post/${postId}`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${session.user.token}`
                    },
                    cache: "no-store"
                });

                if (!response.ok) throw new Error("Failed to fetch");

                const data = await response.json();

                form.reset({
                    prompt: data.prompt,
                    tag: data.tag,
                });

            } catch (error) {
                console.error("Error fetching prompt data:", error);
            }
        };

        if (status === "authenticated" && postId) {
            getPostDetails();
        }

    }, [status, session?.user?.token, postId, router, form]);

    const updatePrompt = async (values: z.infer<typeof schemaPrompt>) => {
        if (!session?.user?.token) return;
        if (!postId) return;

        try {
            const response = await fetch(`${baseUrl}/api/v1/post/update/${postId}`, {
                method: "PATCH",
                headers: {
                    'Authorization': `Bearer ${session.user.token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    prompt: values.prompt,
                    tag: values.tag
                }),
            });

            if (response.ok) {
                router.push("/profile");
                router.refresh();
            } else {
                const errorData = await response.json().catch(() => ({}));
                form.setError("root.serverError", {
                    message: errorData.message || "Error updating prompt."
                });
            }
        } catch (error) {
            console.error("Error updating prompt:", error);
            form.setError("root.serverError", {
                message: "An error occurred while connecting to the server."
            });
        }
    };

    if (status === "loading") {
        return <div className="w-full text-center mt-10">Loading...</div>;
    }

    return (
        <Form
            type="Edit"
            form={form}
            onSubmit={updatePrompt}
        />
    );
};

export default EditPrompt;