import React, { useState } from "react";
import { useLocalState } from "../util/useLocalStorage";
import giftImage from "./gifts.png";
import "./login.css";
import Header from "../Header";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [user, setUser] = useLocalState(null, "user");

  function sendLoginRequest() {
    if (!jwt) {
      const reqBody = {
        username: username,
        password: password,
      };
      fetch("auth/authenticate", {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(reqBody),
      })
        .then((response) => {
          if (response.status === 200)
            return Promise.all([response.json(), response.headers]);
          else return Promise.reject("Invalind login attempt");
        })
        .then(([body, headers]) => {
          setJwt(headers.get("authorization"));
          setUser(body);
          if (body.role === "USER") {
            window.location.href = "certificates";
          } else if(body.role === "ADMIN"){
            window.location.href = "adminPage";
          }
        })
        .catch((message) => {
          alert(message);
        });
    }
  }

  return (
    <>
      <Header />
      <main className="login-main">
        <div className="reg-form">
          <div className="round-image-div">
            <img className="round-image" src={`${giftImage}`} alt="" />
          </div>
          <div className="reg-form-div">
            <input
              className="reg-data-input"
              placeholder="Login"
              id="username"
              value={username}
              onChange={(event) => setUsername(event.target.value)}
            />

            <input
              className="reg-data-input"
              placeholder="Password"
              type="password"
              id="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />

            <input
              className="reg-submit"
              id="submit"
              value={"Login"}
              type="button"
              onClick={() => sendLoginRequest()}
            />
          </div>
        </div>
      </main>
    </>
  );
};

export default Login;
