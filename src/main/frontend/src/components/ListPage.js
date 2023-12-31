import React, {useEffect, useState} from 'react';
import {blue, CustomButton} from './CustomButton';
import {useNavigate} from 'react-router-dom';
import {red} from '@mui/material/colors';
import {Table, TableBody, TableCell, TableHead, TableRow, TextField} from '@mui/material';

const ListPage = () => {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState();
  const [posts, setPosts] = useState();

  useEffect(() => {
    async function fetchData() {
      await fetch('http://localhost:8080/api/posts',{
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': localStorage.getItem('X-AUTH-TOKEN')
        },
      })
      .then(res => res.json()).then(res => setPosts([...res.posts]))
      .catch((err) => console.error(err));
    }

    fetchData();
  }, []);

  const logoutHandler = async () => {
    console.log('logout Token : ' + localStorage.getItem('X-AUTH-TOKEN'))
    const token = localStorage.getItem('X-AUTH-TOKEN');
    if (!token) { // 로그인이 되지 않은 상태일때 회원가입, 로그인 페이지로
      navigate('/login');
      return;
    }
    await fetch(`http://localhost:8080/api/logout`, { // 로그아웃 할때
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': localStorage.getItem('X-AUTH-TOKEN')
      },
      body: JSON.stringify({
        token
      })
    }).then(res => res.json()).then(() => {
      // 23.08.17 hyuna localStorage에서 토큰 삭제
      localStorage.removeItem('X-AUTH-TOKEN');
      navigate('/login');
    }).catch((error) => console.error(error));
    ;
  };

  const searchHandler = async (email) => {
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) return;
    const { posts } = await fetch(`http://localhost:8080/api/posts/search?author_email=${email}`).then(res => res.json()).catch((error) => {
      console.error(error);
    });
    if (!posts) return;
    setPosts([...posts]);
  };


  return (
    <div style={{
      padding: '40px'
    }}>
      <h1>게시판 리스트</h1>
      <div>
        <TextField id="standard-required" label="작성자 이메일 검색" variant="standard" value={keyword}
                   onChange={(event) => setKeyword(event.target.value)}/>
        <CustomButton style={{ backgroundColor: blue[500] }} onClick={() => searchHandler(keyword)}>검색</CustomButton>
      </div>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>이름</TableCell>
            <TableCell>게시물내용</TableCell>
            <TableCell>작성자</TableCell>
            <TableCell>작성일시</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {posts.map(post => (
            <TableRow
              key={post.postId}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              onClick={() => {
                localStorage.setItem('post', JSON.stringify({ ...post}))
                navigate(`/post/${post.id}`)
              }}
            >
              <TableCell component="th" scope="row">
                {post.title}
              </TableCell>
              <TableCell>{post.content}</TableCell>
              <TableCell>{post.author}</TableCell>
              <TableCell>{post.createdAt}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <CustomButton style={{ backgroundColor: blue[500] }} onClick={() => navigate('/post/create')}>게시글 작성</CustomButton>
      <CustomButton style={{ backgroundColor: red[500] }} onClick={logoutHandler}>로그아웃</CustomButton>
    </div>
  );
};

export default ListPage;
