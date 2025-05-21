import Dropdown from 'react-bootstrap/Dropdown';
import { useNavigate } from 'react-router-dom';

function PermissionRequests({ requests }) {
    const navigate = useNavigate();

    return (
        <Dropdown>
            <Dropdown.Toggle variant="info" id="dropdown-basic">
                Requests
            </Dropdown.Toggle>
            {requests.data && requests.data.length > 0 &&
                <Dropdown.Menu>
                {requests.data.map((request, index) => {
                    return (<Dropdown.Item onClick={() => navigate(`/users/${request.userId}`)} key={index}>{request.requestId}</Dropdown.Item>);
                })}
                </Dropdown.Menu>    
            }
        </Dropdown>
    );
}

export default PermissionRequests;