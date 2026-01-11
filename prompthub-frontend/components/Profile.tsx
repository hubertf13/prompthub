import { Post } from "../types"
import PostCard from "./PostCard"

interface ProfileProps {
  name: string;
  desc: string;
  username: string;
  data: Post[];
  handleEdit?: (post: Post) => void;
  handleDelete?: (post: Post) => void;
}

const Profile = ({ name, desc, username, data, handleEdit, handleDelete }: ProfileProps) => {
  return (
    <section className="w-full">
      <h1 className="head_text text-left">
        <span className="blue_gradient">{name} Profile</span>
      </h1>
      <p className="desc text-left">{desc} <span className="font-bold">{username}</span></p>

      <div className="mt-10 prompt_layout">
        {data.map((post) => (
          <PostCard
            key={post.id}
            post={post}
            handleEdit={handleEdit ? () => handleEdit(post) : undefined}
            handleDelete={handleDelete ? () => handleDelete(post) : undefined}
          />
        ))}
      </div>
    </section>
  )
}

export default Profile