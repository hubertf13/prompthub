import { z } from "zod";

export const schemaLogin = z.object({
    email: z.email("Please provide a valid email address."),
    password: z.string().min(1, "Password is required"),
});

export const schemaRegister = z.object({
    username: z.string().min(3, "Username must be at least 3 characters long"),
    email: z.email("Invalid email address"),
    password: z.string().min(5, "The password must be at least 5 characters long."),
    passwordConfirm: z.string(),
}).refine((data) => data.password === data.passwordConfirm, {
    message: "Passwords must be identical",
    path: ["passwordConfirm"],
});

export const schemaPrompt = z.object({
  prompt: z.string().min(3, "Prompt must be at least 3 characters long."),
  tag: z.string().min(1, "Tag is required (e.g. #product, #webdevelopment)."),
});