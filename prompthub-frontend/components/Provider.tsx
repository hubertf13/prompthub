"use client";

import { SessionProvider } from "next-auth/react";
import { Session } from "next-auth";
import React from "react";

interface ProviderProps {
  children: React.ReactNode;
  session?: Session | null;
}

const Provider = ({ children, session }: ProviderProps) => (
  <SessionProvider session={session}>
    {children}
  </SessionProvider>
);

export default Provider;