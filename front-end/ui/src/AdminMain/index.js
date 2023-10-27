import React, { useEffect, useState } from "react";
import { useLocalState } from "../util/useLocalStorage";
import Header from "../Header";
import "./adminPage.css";

const AdminMain = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [user, setUser] = useLocalState(null, "user");
  const [certificates, setCertificates] = useState(null);
  const [pages, setPages] = useState("1");
  const urlSearchParams = new URLSearchParams(window.location.search);
  let page = urlSearchParams.get("p") ? urlSearchParams.get("p") : "1";
  let pageContent;

  function handleSelectChange(event) {
    const selectedValue = event.target.value;
    window.location.href = `/adminPage?p=${selectedValue}`;
  }

  function deleteCertificate(id){
    fetch(`certificate/${id}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
      method: "DELETE",
    })
    window.location.reload();
  }

  useEffect(() => {
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
  }, []);

  if (user && user.role === "ADMIN" && certificates) {
    pageContent = (
      <main className="admin-main">
        <div className="table-div">
          <table>
            <thead>
              <tr>
                <th>Datetime</th>
                <th>Title</th>
                <th>Tag</th>
                <th>Description</th>
                <th>Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {certificates.map((certificate) => (
                <tr key={certificate.id}>
                  <td>{certificate.createDate}</td>
                  <td>{certificate.name}</td>
                  <td>{certificate.tags[0].name}</td>
                  <td>{certificate.description}</td>
                  <td>{certificate.price}</td>
                  <td>
                    <button className="view-button" onClick={()=>window.location.href=`/certificate/${certificate.id}`}>View</button>
                    <button className="update-button" onClick={()=>window.location.href=`/updateCertificate/${certificate.id}`}>Update</button>
                    <button className="delete-button" onClick={()=>deleteCertificate(certificate.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div>
            <select className="select" onChange={handleSelectChange} defaultValue={page}>
              {[...Array(pages)].map((_, i) => (
                <option key={i} value={i + 1} >
                  {i + 1}
                </option>
              ))}
            </select>
          </div>
        </div>
      </main>
    );
  } else {
    pageContent = (
      <div style={{ padding: "75px" }}>
        Ooops... You have not permissions to visit this page
      </div>
    );
  }

  return (
    <>
      <Header />
      {pageContent}
    </>
  );
};

export default AdminMain;
