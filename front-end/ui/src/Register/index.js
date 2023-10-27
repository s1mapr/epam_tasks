import React, { useEffect, useState } from "react";
import Header from "../Header";
import "./register.css";

const Register = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [repeatedPassword, setRepeatedPassword] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [address, setAddress] = useState("");
  const [passwordMismatch, setPasswordMismatch] = useState(false);

  useEffect(()=>{
    if (repeatedPassword !== password) {
        setPasswordMismatch(true);
      } else {
        setPasswordMismatch(false);
      }
  },[repeatedPassword])
  
  

  function sendRegisterRequest() {
    const reqBody = {
      username: login,
      password: password,
      email: email,
      address: address,
      firstName: firstName,
    };

    if (!passwordMismatch) {
      fetch("auth/register", {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(reqBody),
      }).then((response) => {
        if (response.status === 200) window.location.href = "/login";
      });
    }
  }

  return (
    <>
      <Header />
      <main className="register-main">
        <div className="form-div">
          <div className="reg-header">
            <p>Register</p>
          </div>
          <div className="registration-form">
            <div className="columns">
              <div className="column">
                <div className="data-div">
                  <label htmlFor="login">Login name</label>
                  <input
                    id="login"
                    value={login}
                    onChange={(event) => setLogin(event.target.value)}
                  />
                </div>
                <div className="data-div">
                  <label htmlFor="password">Password</label>
                  <input
                    id="password"
                    value={password}
                    type="password"
                    onChange={(event) => setPassword(event.target.value)}
                  />
                </div>
                <div className="data-div">
                  <label htmlFor="email">Email</label>
                  <input
                    id="email"
                    value={email}
                    type="email"
                    onChange={(event) => setEmail(event.target.value)}
                  />
                </div>
              </div>
              <div className="column">
                <div className="data-div">
                  <label htmlFor="firstName">First Name</label>
                  <input
                    id="firstName"
                    value={firstName}
                    onChange={(event) => setFirstName(event.target.value)}
                  />
                </div>
                <div className="data-div">
                  <label htmlFor="repPassword">Repeat Password</label>
                  <input
                    id="repPassword"
                    value={repeatedPassword}
                    type="password"
                    onChange={(event) =>
                      setRepeatedPassword(event.target.value)
                    }
                  />
                  {passwordMismatch && (
                    <p style={{ color: "red", fontSize: "12px" }}>
                      Passwords do not match
                    </p>
                  )}
                </div>
                <div className="data-div">
                  <label htmlFor="address">Address</label>
                  <input
                    id="address"
                    value={address}
                    onChange={(event) => setAddress(event.target.value)}
                  />
                </div>
              </div>
            </div>
            <div className="buttons">
              <a href="/login " className="cancel-button">
                Cancel
              </a>
              <input
                value={"Register"}
                id="submit"
                type="button"
                class="submit"
                onClick={() => sendRegisterRequest()}
              />
            </div>
          </div>
        </div>
      </main>
    </>
  );
};

export default Register;
