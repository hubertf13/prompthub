"use client"

import Link from 'next/link'
import Image from 'next/image'
import { useState } from 'react'
import { signOut, useSession } from 'next-auth/react'

const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

const Nav = () => {
    const { data: session } = useSession();
    const [toggleDropdown, setToggleDropdown] = useState(false)

    const handleSignOut = async () => {
        try {
            if (session?.user?.token) {
                await fetch(`${baseUrl}/api/v1/auth/logout`, {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${session.user.token}`,
                        "Content-Type": "application/json"
                    }
                });
            }
        } catch (error) {
            console.error("Error logging out from backend:", error);
        } finally {
            await signOut({ callbackUrl: '/login' });
        }
    };

    return (
        <nav className='flex-between w-full mb-16 pt-3'>
            <Link href='/' className='flex gap-2 flex-center'>
                <Image
                    src='/assets/images/logo.svg'
                    alt='PromptHub Logo'
                    width={30}
                    height={30}
                    className='object-contain'
                />
                <p className='logo_text'>PromptHub</p>
            </Link>

            <div className='sm:flex hidden'>
                {session?.user ? (
                    <div className='flex gap-3 md:gap-5'>
                        <Link href='/create-prompt' className='black_btn'>
                            Create Post
                        </Link>

                        <button type='button' onClick={handleSignOut} className='outline_btn'>
                            Sign Out
                        </button>

                        <Link href='/profile'>
                            <Image
                                src={session?.user.image || '/assets/images/logo.svg'}
                                width={37}
                                height={37}
                                className='rounded-full'
                                alt='profile'
                            />
                        </Link>
                    </div>
                ) : (
                    <Link href="/login" className='black_btn'>
                        Sign In
                    </Link>
                )}
            </div>

            <div className='sm:hidden flex relative'>
                {session?.user ? (
                    <div className='flex'>
                        <Image
                            src={session?.user.image || '/assets/images/logo.svg'}
                            width={37}
                            height={37}
                            className='rounded-full'
                            alt='profile'
                            onClick={() => setToggleDropdown((prev) => !prev)}
                        />

                        {toggleDropdown && (
                            <div className='dropdown'>
                                <Link
                                    href='/profile'
                                    className='dropdown_link'
                                    onClick={() => setToggleDropdown(false)}
                                >
                                    My Profile
                                </Link>
                                <Link
                                    href='/create-prompt'
                                    className='dropdown_link'
                                    onClick={() => setToggleDropdown(false)}
                                >
                                    Create Prompt
                                </Link>
                                {/* ZMIANA: Tutaj również nasza funkcja */}
                                <button
                                    type='button'
                                    onClick={() => {
                                        setToggleDropdown(false);
                                        handleSignOut();
                                    }}
                                    className='mt-5 w-full black_btn'
                                >
                                    Sign Out
                                </button>
                            </div>
                        )}
                    </div>
                ) : (
                    <Link href="/login" className='black_btn'>
                        Sign In
                    </Link>
                )}
            </div>
        </nav>
    )
}

export default Nav