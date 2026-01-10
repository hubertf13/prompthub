"use client";

import { useEffect } from "react";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

import { schemaPrompt } from "../../../lib/schemas";
import { Form } from "../../../components/Form";

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

const CreatePrompt = () => {
    const { data: session, status } = useSession();
    const router = useRouter();

    useEffect(() => {
        if (status === "unauthenticated") {
            router.push("/login");
        }
    }, [status, router]);

    const form = useForm<z.infer<typeof schemaPrompt>>({
        resolver: zodResolver(schemaPrompt),
        defaultValues: {
            prompt: "",
            tag: "",
        },
    });

    const createPrompt = async (values: z.infer<typeof schemaPrompt>) => {
        if (!session?.user?.token) {
            form.setError("root.serverError", { message: "You are not logged in." });
            return;
        }

        try {
            const response = await fetch(`${baseUrl}/api/v1/post/add`, {
                method: "POST",
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
                router.push("/");
                router.refresh();
            } else {
                const errorData = await response.json().catch(() => ({}));
                form.setError("root.serverError", {
                    message: errorData.message || "Error creating prompt."
                });
            }
        } catch (error) {
            console.error("Error creating prompt:", error);
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
            type="Create"
            form={form}
            onSubmit={createPrompt}
        />
    );
};

export default CreatePrompt;