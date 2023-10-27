import React, { useEffect, useState } from "react";
import { useLocalState } from "../util/useLocalStorage";
import Header from "../Header";
import "./certificate.css";

const Certificate = () => {
  const certificateId = window.location.href.split("/certificate/")[1];
  const [certificate, setCertificate] = useState(null);
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [user, setUser] = useLocalState(null, "user");
  console.log(user);
  const addToCartLocation = user ? "/checkout" : "/login";
  let addToCartBlock;
  function addToCart() {
    if (user) {
      let certificates = JSON.parse(localStorage.getItem("certificates")) || [];
      certificates.push(certificate);
      localStorage.setItem("certificates", JSON.stringify(certificates));
    }
    window.location.href = addToCartLocation;
  }

  

  useEffect(() => {
    fetch(`/certificate/${certificateId}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
      method: "GET",
    })
      .then((response) => {
        if (response.status === 200) return response.json();
      })
      .then((certificateData) => {
        setCertificate(certificateData);
      });
  }, []);

  if (certificate && user && user.role === "ADMIN") {
    addToCartBlock = <div> </div>;
  } else if(certificate){
    addToCartBlock = (
      <div class="price-cart">
        <p>{certificate.price}$</p>
        <a class="add-cart" href="#" onClick={() => addToCart()}>
          Add to cart
        </a>
      </div>
    );
  }

  return (
    <>
      <Header />
      {certificate ? (
        <main className="certificate-main">
          <div class="main-block">
            <div
              class="certificate-img"
              style={{
                backgroundImage: `url(data:image/jpeg;base64,${certificate.image})`,
              }}
            ></div>

            <div class="certificate-info">
              <h3>{certificate.name}</h3>
              <p>Duration {certificate.duration} monthes</p>
              {addToCartBlock}
            </div>
          </div>

          <div class="certificate-description">
            <h2>Item Detailed Description</h2>
            <p>{certificate.description}</p>
          </div>
        </main>
      ) : (
        <div>Loading...</div>
      )}
    </>
  );
};

export default Certificate;
