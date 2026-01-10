export interface Author {
  id: number;
  username: string;
  email: string;
}

export interface Post {
  id: number;
  prompt: string;
  tag: string;
  author: Author;
}