import NextAuth from "next-auth";

declare module "next-auth" {
  interface User {
    username: string;
    token: string;
  }

  interface Session {
    user: User & {
      username: string;
      token: string;
    };
    token: string; // Dodatkowy dostęp do tokena bezpośrednio w sesji
  }
}

import { JWT } from "next-auth/jwt";

declare module "next-auth/jwt" {
  interface JWT {
    username: string;
    token: string;
  }
}