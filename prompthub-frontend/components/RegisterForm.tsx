import { UseFormReturn } from "react-hook-form";
import Link from "next/link";
import { z } from "zod";
import { schemaRegister } from "../lib/schemas";

export function RegisterForm({
  form,
  onSubmit,
}: {
  form: UseFormReturn<z.infer<typeof schemaRegister>>;
  onSubmit: (values: z.infer<typeof schemaRegister>) => Promise<void>;
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
          <h2 className="text-3xl font-bold text-gray-900">Registration</h2>
          <p className="text-gray-500 mt-2 text-sm">
            Create an account to get started
          </p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          
          <div className="space-y-1">
            <label className="block text-sm font-medium text-gray-700">Username</label>
            <input
              type="text"
              placeholder="username"
              {...register("username")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.username ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.username && <p className="text-red-500 text-sm">{errors.username.message}</p>}
          </div>

          <div className="space-y-1">
            <label className="block text-sm font-medium text-gray-700">Email</label>
            <input
              type="email"
              placeholder="email@example.com"
              {...register("email")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.email ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
          </div>

          <div className="space-y-1">
            <label className="block text-sm font-medium text-gray-700">Password</label>
            <input
              type="password"
              placeholder="••••••••"
              {...register("password")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.password ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
          </div>

          <div className="space-y-1">
            <label className="block text-sm font-medium text-gray-700">Confirm password</label>
            <input
              type="password"
              placeholder="••••••••"
              {...register("passwordConfirm")}
              className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-black focus:border-transparent transition-all ${
                errors.passwordConfirm ? "border-red-500" : "border-gray-300"
              }`}
            />
            {errors.passwordConfirm && <p className="text-red-500 text-sm">{errors.passwordConfirm.message}</p>}
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
            {isSubmitting ? "Registration..." : "Register"}
          </button>
        </form>
      </div>

      <div className="mt-6 text-center text-sm text-gray-600">
        Already have an account?
        <Link className="font-semibold text-black underline ml-2 hover:text-gray-800" href="/login">
          Log in
        </Link>
      </div>
    </div>
  );
}