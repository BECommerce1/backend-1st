import React, {useEffect, useState} from 'react';
import {Card, CardContent, TextField, Typography} from '@mui/material';
import {red} from '@mui/material/colors';
import {blue, CustomButton} from './CustomButton';
import {StyledTextarea} from './StyledTextArea';
import {useNavigate} from 'react-router-dom';

const PostDetailPage = () => {
  const navigate = useNavigate();
  const [post, setPost] = useState({});
  const [comments, setComments] = useState([{
      id: 1,
      content: 1,
      author: '작성자1',
      post_id: 3,
      created_at: '작성일시'
    },
    {
      id: 2,
      content: 1,
      author: '작성자2',
      post_id: 1,
      created_at: '작성일시'
    },
    {
      id: 3,
      content: 1,
      author: '작성자3',
      post_id: 2,
      created_at: '작성일시'
    }]);
  const [like, setLike] = useState(false)

  //const sessionId = localStorage.getItem('sessionId');
  const sessionId = 1;
  const [newComment, setNewCommnent] = useState({
    content: '',
    author: ''
  })

  async function fetchData() {
    await fetch('http://localhost:8080/api/comments')
    .then(res => res.json()).then(res => {
      if(!res) return;
      setComments([...res.comments.filter(c => c?.post_id === post.id)])
    })
    .catch((err) => console.error(err));
  }


  const fetchComment = async (id,post_id) => {
    const response = await fetch(`/v1/api/check_like?memberId=${id}&replyId=${post_id}`);
    const data = await response.json();
    return data;
  };


  useEffect(() => {
    const postData = JSON.parse(localStorage.getItem('post'));
    setPost({ ...postData });
    try {
      fetchData();
    } catch (e) {
      console.error(e)
    }
  }, []);


  const handlePostChange = async () => {
    await fetch(`http://localhost:8080/api/posts/${post.id}`, {
      method: 'PUT',
      body: JSON.stringify({
        title: post?.title || '',
        content: post?.content || ''
      })
    }).catch((err) => console.error(err));
  };

  const handleCommentChange = async (id, content) => {
    await fetch(`http://localhost:8080/api/comments/${id}`, {
      method: 'PUT',
      body: JSON.stringify({
        content: content
      })
    }).catch((err) => console.error(err));
  };


  const handleCommentInsert = async (id, post_id) => {
    const comment = await fetchComment(id,post_id);
    if (comment == false) {
      // 댓글에 no가 없을 경우
      const response = await fetch(`/v1/api/insert_like`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Referrer-Policy': 'no-referrer'
        },
        body: JSON.stringify({
          memberId: id,
          replyId: post_id
        })
      });
      if (response.status === 200) {
        // 좋아요가 성공적으로 추가된 경우
        document.getElementById(post_id).innerHTML = '취소';
        document.getElementById(post_id).style.backgroundColor = 'red';
      }
    } else {
      // 댓글에 no가 있을 경우
      const response = await fetch(`/v1/api/delete_like/${id}/${post_id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Referrer-Policy': 'no-referrer'
        },
        body: JSON.stringify({
          memberId: id,
          replyId: post_id
        })
      });
      if (response.status === 200) {
        // 좋아요가 성공적으로 삭제된 경우
        document.getElementById(post_id).innerHTML = '좋아요';
        document.getElementById(post_id).style.backgroundColor = 'rgb(0, 114, 229)';
      }
    }
  };

  const changeComment = (commentId, comment) => {
    const indexToUpdate = comments.findIndex((item) => item.id === commentId);

    const newComments = comments;
    if (indexToUpdate !== -1) {
      newComments[indexToUpdate] = {
        ...newComments[indexToUpdate],
        content: comment
      };
      setComments([...newComments])
    }
  }

  const submitComment = async () => {
    await fetch(`http://localhost:8080/api/comments`, {
      method: 'POST',
      body: JSON.stringify({
        author: newComment.author,
        content: newComment.content,
        post_id: post.id
      })
    }).catch((err) => console.error(err));
  }
  const likes = comments.map(c => c.id);

  const isLiked = async(id, post_id) => {
    const response = await fetch(`/v1/api/check_like?memberId=${id}&replyId=${post_id}`, {
      method: 'GET',
    });
    const d = await response.json();
    if(d == true){
      const element = document.getElementById(post_id);
      element.innerHTML = '취소';
      document.getElementById(post_id).style.backgroundColor = 'red';
    }else{
      const element = document.getElementById(post_id);
      element.innerHTML = '좋아요';
      document.getElementById(post_id).style.backgroundColor = 'rgb(0, 114, 229)';
    }
    return '';
  };

  return (
    <div style={{
      padding: '40px'
    }}>
      <h1>게시판 상세</h1>
      <h2>글 제목</h2>
      <TextField id="outlined-basic" label="Outlined" variant="outlined" value={post?.title || ''}
                 onChange={(event) => setPost(prev => ({
                   ...prev,
                   title: event.target.value
                 }))}/>
      <h2>작성자</h2>
      <TextField id="outlined-basic" label="Outlined" variant="outlined" value={post?.author || ''}
                 onChange={(event) => setPost(prev => ({
                   ...prev,
                   author: event.target.value
                 }))}/>
      <h2>본문</h2>
      <StyledTextarea
        aria-label="minimum height"
        minRows={3}
        placeholder="Minimum 3 rows"
        value={post?.content || ''}
        onChange={(event) => setPost(prev => ({
          ...prev,
          content: event.target.value
        }))}
      />
      <div style={{
        marginTop: '20px'
      }}>
        <CustomButton style={{ backgroundColor: blue[500] }} onClick={handlePostChange}>수정</CustomButton>
        <CustomButton style={{ backgroundColor: red[500] }} onClick={() => navigate('/')}>취소</CustomButton>
      </div>
      <div style={{marginTop: 20}}>
        <Card sx={{ marginBottom: 2 }}>
          <CardContent style={{display: 'flex', flexDirection: 'column'}}>
            <h3>댓글 작성자</h3>
            <TextField variant="outlined" value={newComment.author || ''}
                       onChange={(event) => setNewCommnent(prev => ({...prev, author: event.target.value}))}/>
            <h3>댓글 내용</h3>
            <TextField variant="outlined" value={newComment.content || ''}
                       onChange={(event) => setNewCommnent(prev => ({...prev, content: event.target.value}))}/>
            <CustomButton style={{ backgroundColor: blue[500], marginTop: 10 }} onClick={submitComment}>생성</CustomButton>
          </CardContent>
        </Card>
        {comments.length > 0 && (
          comments.map((c, index) => (
            <Card sx={{ marginBottom: 2 }}>
              <CardContent>
                <TextField variant="outlined" value={c?.content || ''}
                           onChange={(event) => changeComment(c.id, event.target.value)}/>
                <Typography variant="h5" component="div">
                  {c?.author || ''}
                </Typography>
                <Typography color="text.secondary">
                  {c?.created_at || ''}
                </Typography>
                <CustomButton style={{ backgroundColor: blue[500] }} onClick={() => handleCommentChange(c.id, c.content)}>수정</CustomButton>
                {isLiked(sessionId, c.post_id) ? "" : ""}
                <CustomButton id={c.id} class="like-button" style={{ marginTop: 10 }} onClick={() => handleCommentInsert(sessionId, c.id)}>좋아요</CustomButton>

              </CardContent>
            </Card>
          )))
        }
      </div>
    </div>
  );
};

export default PostDetailPage;
