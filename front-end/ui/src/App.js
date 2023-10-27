import { Route, Routes } from "react-router-dom";
import { useLocalState } from "./util/useLocalStorage";
import Login from "./Login";
import PrivateRoute from "./PrivateRoute";
import CreateCertificates from "./CreateCertificates";
import Certificates from "./Certificates";
import Certificate from "./Certificate";
import Register from "./Register";
import UpdateCertificate from "./UpdateCertificate";
import Checkout from "./Checkout";
import AdminMain from "./AdminMain";

function App() {
  const [jwt, setJwt] = useLocalState("", "jwt");

  return (
    <Routes>
      <Route
        path="/checkout"
        element={
          <PrivateRoute>
            <Checkout />
          </PrivateRoute>
        }
      />

      <Route path="certificates" element={<Certificates />} />

      <Route path="certificate/:id" element={<Certificate />} />

      <Route
        path="updateCertificate/:id"
        element={
          <PrivateRoute>
            <UpdateCertificate />
          </PrivateRoute>
        }
      />

      <Route
        path="adminPage"
        element={
          <PrivateRoute>
            <AdminMain />
          </PrivateRoute>
        }
      />

      <Route path="/createCertificate" element={<CreateCertificates />} />

      <Route path="/login" element={<Login />}></Route>

      <Route path="/register" element={<Register />}></Route>
    </Routes>
  );
}

export default App;
