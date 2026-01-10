import { UseFormReturn } from "react-hook-form";
import Link from "next/link";
import { z } from "zod";
import { schemaPrompt } from "../lib/schemas";

interface FormProps {
  type: "Create" | "Edit";
  form: UseFormReturn<z.infer<typeof schemaPrompt>>;
  onSubmit: (values: z.infer<typeof schemaPrompt>) => Promise<void>;
}

export function Form({ type, form, onSubmit }: FormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = form;

  return (
    <section className="w-full max-w-full flex-start flex-col">
      <h1 className="head_text text-left">
        <span className="blue_gradient">{type} Post</span>
      </h1>
      <p className="desc text-left max-w-md">
        {type} and share amazing prompts with the world, and let your imagination run wild with any AI-powered platform.
      </p>

      <form
        onSubmit={handleSubmit(onSubmit)}
        className="mt-10 w-full max-w-2xl flex flex-col gap-7 glassmorphism"
      >
        <label>
          <span className="font-satoshi font-semibold text-base text-gray-700">
            Your AI Prompt
          </span>
          <textarea
            placeholder="Write your prompt here..."
            className={`form_textarea ${errors.prompt ? "border-red-500" : ""}`}
            {...register("prompt")}
          />
          {errors.prompt && (
            <p className="text-red-500 text-sm mt-1">{errors.prompt.message}</p>
          )}
        </label>

        <label>
          <span className="font-satoshi font-semibold text-base text-gray-700">
            Tag {` `}
            <span className="font-normal">(#product, #webdevelopment, #idea)</span>
          </span>
          <input
            type="text"
            placeholder="#tag"
            className={`form_input ${errors.tag ? "border-red-500" : ""}`}
            {...register("tag")}
          />
          {errors.tag && (
            <p className="text-red-500 text-sm mt-1">{errors.tag.message}</p>
          )}
        </label>

        {errors.root?.serverError && (
          <div className="p-3 bg-red-50 border border-red-200 rounded text-red-600 text-sm">
            {errors.root.serverError.message}
          </div>
        )}

        <div className="flex-end mx-3 mb-5 gap-4">
          <Link href="/" className="text-gray-500 text-sm">
            Cancel
          </Link>

          <button
            type="submit"
            disabled={isSubmitting}
            className="px-5 py-1.5 text-sm bg-primary-orange rounded-full text-white disabled:opacity-50"
          >
            {isSubmitting ? `${type}...` : type}
          </button>
        </div>
      </form>
    </section>
  );
}