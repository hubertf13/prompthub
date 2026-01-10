"use client";

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { schemaRegister } from "../../../lib/schemas";
import { RegisterForm } from "../../../components/RegisterForm";

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

export default function RegisterPage() {
  const router = useRouter();

  const form = useForm<z.infer<typeof schemaRegister>>({
    resolver: zodResolver(schemaRegister),
    defaultValues: {
      username: "",
      email: "",
      password: "",
      passwordConfirm: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof schemaRegister>) => {
    try {
      const response = await fetch(`${baseUrl}/api/v1/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        
        body: JSON.stringify({
          username: values.username,
          email: values.email,
          password: values.password
        }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        form.setError("root.serverError", {
          message: errorData.message || "Registration error. Please try again.",
        });
        return;
      }

      router.push("/login");

    } catch (error) {
      form.setError("root.serverError", {
        message: "An error occurred while connecting to the server.",
      });
    }
  };

  return (
    <div className="flex h-screen w-full items-center justify-center bg-gray-50 px-4">
      <RegisterForm form={form} onSubmit={onSubmit} />
    </div>
  );
}