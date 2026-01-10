"use client"

import { useState, useEffect } from "react";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";

import { Post } from "../../../types";
import Profile from "../../../components/Profile";

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

const MyProfile = () => {
    const router = useRouter();
    const { data: session } = useSession();

    const [allPosts, setAllPosts] = useState<Post[]>([]);

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await fetch(`${baseUrl}/api/v1/post/all/user/me`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${session?.user.token}`
                    }
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
    }, []);

    const handleEdit = (post: Post) => {
        router.push(`/update-prompt?id=${post.id}`);
    }

    const handleDelete = async (post: Post) => {
        try {
            const response = await fetch(`${baseUrl}/api/v1/post/${post.id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${session?.user.token}`
                }
            });

            if (response.ok) {
                const updatedPosts = allPosts.filter(p => p.id !== post.id);
                setAllPosts(updatedPosts);
            } else {
                console.error("Failed to delete post");
            }
        } catch (error) {
            console.error("Error deleting post:", error);
        }
    }

    return (
        <Profile
            name="My"
            desc="Welcome to your personalized profile page"
            data={allPosts}
            handleEdit={handleEdit}
            handleDelete={handleDelete}
        />
    )
}

export default MyProfile