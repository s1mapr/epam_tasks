import React, { useEffect } from "react";
import { useLocalState } from "../util/useLocalStorage";
import Header from "../Header";
import { Link } from "react-router-dom";
import "./checkout.css"

const Checkout = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [user, setUser] = useLocalState(null, "user");
  const certificatesStr = localStorage.getItem("certificates");
  const certificates = JSON.parse(certificatesStr);
  let pageContent;

  function createOrders() {
    fetch("/order?userId=" + user.id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
      body: JSON.stringify(certificates),
    }).then((response) => {
      if (response.status === 200) {
        localStorage.removeItem("certificates");
        window.location.href = "/certificates";
      } else {
        console.error("Ошибка при создании заказа");
      }
    });
  }
  console.log(certificates);
  if(certificates){
  pageContent = 
    certificates.map((certificate) => (
    <Link to={`/certificate/${certificate.id}`} className="checkout-link">
      <div className="checkout-coupon">
        <div
          className="checkout-img"
          style={{
            backgroundImage: `url(data:image/jpeg;base64,${certificate.image})`,
          }}
        ></div>
        <div class="checkout-info">
          <div className="checkout-info-desc">
            <h4>{certificate.name}</h4>
            <p>Duration {certificate.duration} months</p>
          </div>
          <div class="checkout-price">
            <p>{certificate.price}$</p>
          </div>
        </div>
      </div>
    </Link>
    )
    );
  } else {
    pageContent = (
      <div>You have not any certificates in cart</div>
    );
  }


  return (
    <>
      <Header />
      <main className="checkout-main">
        <div class="checkout-main-header">
          <p>Checkout</p>
        </div>
        <div class="checkout-main-container">
          <div class="checkout-coupons">
            {pageContent}
          </div>
          
          <div class="checkout-buttons">
          <button className="checkout-checkout" onClick={() => createOrders()}>Checkout</button>

          </div>
        </div>
      </main>
    </>
  );
};

export default Checkout;
