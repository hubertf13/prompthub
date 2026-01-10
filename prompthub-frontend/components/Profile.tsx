import { Post } from "../types"
import PostCard from "./PostCard"

const Profile = ({ name, desc, data, handleEdit, handleDelete }: {
  name: string,
  desc: string,
  data: Post[],
  handleEdit: (post: Post) => void,
  handleDelete: (post: Post) => void
}) => {
  return (
    <section className="w-full">
      <h1 className="head_text text-left">
        <span className="blue_gradient">{name} Profile</span>
      </h1>
      <p className="desc text-left">{desc}</p>
      <div className="mt-10 prompt_layout">
        {data.map((post) => (
          <PostCard
            key={post.id}
            post={post}
            handleEdit={() => handleEdit && handleEdit(post)}
            handleDelete={() => handleDelete && handleDelete(post)}
          />
        ))}
      </div>
    </section>
  )
}

export default Profile