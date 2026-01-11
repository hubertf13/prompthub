"use client"

import { useState, useEffect } from "react";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";

import { Post } from "../../../types";
import Profile from "../../../components/Profile";

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

const MyProfile = () => {
    const router = useRouter();
    const { data: session, status } = useSession();

    const [allPosts, setAllPosts] = useState<Post[]>([]);

    useEffect(() => {
        if (status === "unauthenticated") {
            router.push("/login");
            return;
        }

        const fetchPosts = async () => {
            if (!session?.user?.token) return;

            try {
                const response = await fetch(`${baseUrl}/api/v1/post/all/user/me`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${session.user.token}`
                    },
                    cache: "no-store"
                });

                const data = await response.json();

                if (Array.isArray(data)) {
                    setAllPosts(data);
                } else {
                    setAllPosts([]);
                }
            } catch (error) {
                console.error("Failed to fetch posts:", error);
            }
        }

        if (session?.user?.token) {
            fetchPosts();
        }
    }, [session?.user?.token, status, router]);

    const handleEdit = (post: Post) => {
        router.push(`/update-prompt?id=${post.id}`);
    }

    const handleDelete = async (post: Post) => {
        const hasConfirmed = confirm("Are you sure you want to delete this prompt?");

        if (hasConfirmed) {
            try {
                const response = await fetch(`${baseUrl}/api/v1/post/delete/${post.id}`, {
                    method: "DELETE",
                    headers: {
                        "Authorization": `Bearer ${session?.user.token}`
                    }
                });

                if (response.ok) {
                    const updatedPosts = allPosts.filter((p) => p.id !== post.id);
                    setAllPosts(updatedPosts);
                } else {
                    alert("Failed to delete post. Please try again.");
                }
            } catch (error) {
                console.error("Error deleting post:", error);
            }
        }
    }

    if (status === "loading") {
        return <div>Loading...</div>;
    }

    return (
        <Profile
            name="My"
            desc="Welcome to your personalized profile page"
            username={session?.user?.username || ""}
            data={allPosts}
            handleEdit={handleEdit}
            handleDelete={handleDelete}
        />
    )
}

export default MyProfile;