import { UseFormReturn } from "react-hook-form";
import Link from "next/link";
import { z } from "zod";
import { schemaLogin } from "../lib/schemas";

export function LoginForm({
  form,
  onSubmit,
}: {
  form: UseFormReturn<z.infer<typeof schemaLogin>>;
  onSubmit: (values: z.infer<typeof schemaLogin>) => Promise<void>;
}) {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = form;

  return (
    <div className="w-full max-w-md">
      <div className="bg-white shadow-md rounded-lg border border-gray-200 p-6 sm:p-8">
        <div className="mb-6 text-center">
          <h2 className="text-3xl font-bold text-gray-900">Log in</h2>
          <p className="text-gray-500 mt-2 text-sm">
            Enter your credentials to access
          </p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          
          <div className="space-y-1">
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email</label>
            <input
              id="email"
              type="email"
              placeholder="example@mail.com"
              {...register("email")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.email ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
          </div>

          <div className="space-y-1">
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
            <input
              id="password"
              type="password"
              placeholder="••••••••"
              {...register("password")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.password ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
          </div>

          {errors.root?.serverError && (
             <div className="p-3 bg-red-50 border border-red-200 rounded text-red-600 text-sm text-center">
               {errors.root.serverError.message}
             </div>
          )}

          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full bg-black text-white font-medium py-2 px-4 rounded-md hover:bg-gray-800 transition-colors disabled:opacity-50 disabled:cursor-not-allowed mt-2"
          >
            {isSubmitting ? "Logging in..." : "Log in"}
          </button>
        </form>
      </div>

      <div className="mt-6 text-center text-sm text-gray-600">
        Do not have an account?
        <Link className="font-semibold text-black underline ml-2 hover:text-gray-800" href="/register">
          Register
        </Link>
      </div>
    </div>
  );
}