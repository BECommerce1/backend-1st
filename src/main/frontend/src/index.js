import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import LoginPage from './components/LoginPage';
import ListPage from './components/ListPage';
import PostDetailPage from './components/PostDetailPage';
import CreatePostPage from './components/CreatePostPage';

const root = ReactDOM.createRoot(document.getElementById('root'));

const router = createBrowserRouter([
  {
    path: "/",
    element: <ListPage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/post/:documentId",
    element: <PostDetailPage />,
  },
  {
    path: "/post/create",
    element: <CreatePostPage />,
  },
]);

root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
