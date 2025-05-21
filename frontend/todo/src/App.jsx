import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './components/register/RegisterComponent';
import Login from './components/login/LoginComponent';
import UserProfile from './components/users/UserProfileComponent';
import TasksComponent from './components/tasks/TasksComponent';
import './App.css';
import IsTokenValid from './components/login/TokenComponent';
import NotFoundComponent from "./components/NotFound/NotFoundComponent";
import ProtectedLayout from './components/logout/ProtectedComponents';
import Forbidden from "./components/forbiden/ForbiddenComponent";
import UserInfo from './components/users/UserInfoComponent';
import UsersTasks from './components/users/UsersTasksComponent';

function App() {
  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/auth/register" element={<Register />} />
        <Route path="/auth/login" element={<Login />} />
        

        {/* Protected Routes */}
        <Route element={<IsTokenValid />}>
          <Route element={<ProtectedLayout/>}>
            <Route path="/tokens/user" element={<UserProfile />} />
            <Route path="/tasks/:userId" element={<TasksComponent />} />
            <Route path="/users/:userId" element={<UserInfo />} />
            <Route path="/users/tasks" element={<UsersTasks />} />
            <Route path="/auth/forbidden" element={<Forbidden/>}/>
          </Route>
        </Route>

        
        <Route path="*" element={<NotFoundComponent />} />
      </Routes>
    </Router>
  );
}

export default App;
