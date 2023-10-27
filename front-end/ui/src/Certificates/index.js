import React, { useEffect, useState } from "react";
import Header from "../Header";
import { useLocalState } from "../util/useLocalStorage";
import { Link } from "react-router-dom";
import "./certificates.css";

const Certificates = () => {
  const [certificates, setCertificates] = useState(null);
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [user, setUser] = useLocalState("", "user");
  const [pages, setPages] = useState("1");
  const urlSearchParams = new URLSearchParams(window.location.search);
  const name = urlSearchParams.get("name") ? urlSearchParams.get("name") : "";
  const category = urlSearchParams.get("category")
    ? urlSearchParams.get("category")
    : "";
  let page = urlSearchParams.get("p") ? urlSearchParams.get("p") : 1;
  console.log(page);
  let pageContent;

  function handleSelectChange(event) {
    const selectedValue = event.target.value;
    window.location.href = `/certificates?tagName=${category}&name=${name}&p=${selectedValue}`;
  }

  useEffect(() => {
    if (name || category) {
      fetch(`/certificate/search?tagName=${category}&name=${name}&p=${page}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        method: "GET",
      })
        .then((response) => {
          if (response.status === 200) return response.json();
        })
        .then((certificatesData) => {
          setCertificates(certificatesData.certificates);
          setPages(certificatesData.pages);
        });
    } else {
      fetch(`certificate?p=${page}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        method: "GET",
      })
        .then((response) => {
          if (response.status === 200) return response.json();
        })
        .then((certificatesData) => {
          setCertificates(certificatesData.certificates);
          setPages(certificatesData.pages);
        });
    }
  }, []);

  pageContent = (
    <main className="certificates-main">
        <div className="certificates-main-grid">
          {certificates ? (
            certificates.map((certificate) => (
              <Link to={`/certificate/${certificate.id}`} className="link">
                <div className="certificates-block">
                  <div
                    className="certificates-img"
                    style={{
                      backgroundImage: `url(data:image/jpeg;base64,${certificate.image})`,
                    }}
                  ></div>
                  <div class="certificates-info">
                    <h4>{certificate.name}</h4>
                    <p>Duration {certificate.duration} months</p>
                    <div class="certificates-price-cart">
                      <p>{certificate.price}$</p>
                      <a class="certificates-add-cart">Add to cart</a>
                    </div>
                  </div>
                </div>
              </Link>
            ))
          ) : (
            <></>
          )}

        </div>
        <div style={{padding:"20px"}}>
          <select
            className="select"
            onChange={handleSelectChange}
            value={page}
          >
            {[...Array(pages)].map((_, i) => (
              <option key={i} value={i + 1}>
                {i + 1}
              </option>
            ))}
          </select>
        </div>
      </main>
  );

  return (
    <>
      <Header />
      {pageContent}
    </>
  );
};

export default Certificates;
