import React, { useState, useEffect } from 'react';

export default ()=> {
  const [postData, setData] = useState({
    isLoading: false,
    error: null,
    posts: []
  });

  useEffect(()=>{
    setData(prevState => ({...prevState, isLoading: true}));
    setTimeout(()=>{
       fetch('localhost:8080/users')
        .then(res => res.json())
        .then(posts => {
          setData((prevState)=>({
            ...prevState,
            isLoading: false,
            posts
          }));
      })
      .catch(e => {
        setData((prevState)=>({
          ...prevState,
          isLoading: false,
          error: e
        }))
      })
    }, 500)
  }, []);

  const {isLoading, error, posts} = postData;

  return  (
    <>
    {isLoading && 'Loading....'}
    {!isLoading && !error &&
        (posts.length !== 0 
          ? posts.map(post => <Post post={post} key={post.id}/>)
          : 'Empty list')
    }
    {!isLoading && error && 'Error happens'}
    </>
  );
}

function Post({post}){
  return (
    <div style={{marginTop: 10}}>
      <li>{post.title}</li>
      <a href={'/posts/' + post.id} style={{fontSize: 10, color: "blue", float: "right", marginRight: 5, marginTop: -5}}>Read more</a>
      <hr />
    </div>
    
  )
}