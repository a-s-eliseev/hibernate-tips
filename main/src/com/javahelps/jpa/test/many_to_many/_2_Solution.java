package com.javahelps.jpa.test.many_to_many;

import com.javahelps.jpa.test.util.PersistentHelper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class _2_Solution {
    public static void main(String[] args) {
        EntityManager entityManager = PersistentHelper.getEntityManager(new Class[] {Post.class, Tag.class});

        entityManager.getTransaction().begin();

        Post post1 = new Post("Post 1");
        Post post2 = new Post("Post 2");
        Post post3 = new Post("Post 3");

        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.persist(post3);

        Tag tag1 = new Tag("Tag 1");
        Tag tag2 = new Tag("Tag 2");
        Tag tag3 = new Tag("Tag 3");

        entityManager.persist(tag1);
        entityManager.persist(tag2);
        entityManager.persist(tag3);

        List<Tag> firstPostTags = Arrays.asList(tag1, tag2, tag3);
        List<Tag> secondPostTags = Arrays.asList(tag2, tag3);
        List<Tag> thirdPostTags = Arrays.asList(tag3);

        //у первого поста все теги
        post1.setTags(firstPostTags);

        //у второго поста только 2 и 3
        post2.setTags(secondPostTags);

        //у третьего поста только 3
        post3.setTags(thirdPostTags);

        entityManager.getTransaction().commit();

        //проблема решена добавление аттрибута mappedBy на зависимую часть. какую часть делать зависимой - исключительно
        //выбор архитектуры
    }




    @Entity(name = "Post")
    @Table(name = "post")
    public static class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        public Post() {}

        public Post(String title) {
            this.title = title;
        }

        @ManyToMany
        private List<Tag> tags = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Tag> getTags() {
            return tags;
        }

        public void setTags(List<Tag> tags) {
            this.tags = tags;
        }

        public void addTag(Tag tag) {
            tags.add(tag);
            tag.getPosts().add(this);
        }

        public void removeTag(Tag tag) {
            tags.remove(tag);
            tag.getPosts().remove(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Post)) return false;
            return id != null && id.equals(((Post) o).getId());
        }

        @Override
        public int hashCode() {
            return 31;
        }
    }

    @Entity(name = "Tag")
    @Table(name = "tag")
    public static class Tag {

        @Id
        @GeneratedValue
        private Long id;

        private String name;

        @ManyToMany(mappedBy = "tags")
        private List<Post> posts = new ArrayList<>();

        public Tag() {}

        public Tag(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tag tag = (Tag) o;
            return Objects.equals(name, tag.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
