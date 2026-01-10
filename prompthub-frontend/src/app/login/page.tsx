"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import { z } from "zod";

import { schemaLogin } from "../../../lib/schemas";
import { LoginForm } from "../../../components/LoginForm";

export default function LoginPage() {
    const router = useRouter();

    const form = useForm<z.infer<typeof schemaLogin>>({
        resolver: zodResolver(schemaLogin),
        defaultValues: {
            email: "",
            password: "",
        },
    });

    async function onSubmit(values: z.infer<typeof schemaLogin>) {
        try {
            const result = await signIn("credentials", {
                email: values.email,
                password: values.password,
                redirect: false,
            });

            if (result?.error) {
                form.setError("root.serverError", {
                    type: "manual",
                    message: "Incorrect email or password.",
                });
            } else {

                router.push("/");
                router.refresh();
            }
        } catch (error) {
            form.setError("root.serverError", {
                type: "manual",
                message: "An unexpected error occurred.",
            });
        }
    }

    return (
        <div className="flex h-screen w-full items-center justify-center px-4">
            <LoginForm form={form} onSubmit={onSubmit} />
        </div>
    );
}