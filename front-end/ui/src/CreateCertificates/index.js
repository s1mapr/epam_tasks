import React, { useState } from "react";
import axios from "axios";
import { useLocalState } from "../util/useLocalStorage";
import Header from "../Header";
import "./createCertificate.css";

const CreateCertificates = () => {
  const [certificateName, setCertificateName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [duration, setDuration] = useState("");
  const [image, setImage] = useState(null);
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [tags, setTags] = useState("Sport");

  async function createCertificate() {
    if (jwt) {
      const formData = new FormData();
      formData.append("name", certificateName);
      formData.append("description", description);
      formData.append("price", price);
      formData.append("duration", duration);
      formData.append("image", image);
      formData.append("tags", tags);

      try {
        const response = await axios.post("certificate", formData, {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "multipart/form-data",
          },
        });

        if (response.status === 200) {
          window.location.href = `/adminPage`;
        } else {
          console.error("Ошибка сервера");
        }
      } catch (error) {
        console.error("Произошла ошибка:", error);
      }
    }
  }

  return (
    <>
      <Header />
      <main class="create-certificate-main">
        <div class="create-certificate-coupon-header">
          <p>Add New Coupon</p>
        </div>
        <div className="create-certificate-coupon-main">
          <div className="create-certificate-column">
            <div className="create-certificate-data">
              <label htmlFor="name">Certificate name</label>
              <input
                id="name"
                value={certificateName}
                onChange={(event) => setCertificateName(event.target.value)}
              />
            </div>
            <div className="create-certificate-data">
              <label for="category">Category</label>
              <select id="category" name="category" required onChange={(event) => setTags(event.target.value)}>
                <option value="Sport">Sport</option>
                <option value="Beauty">Beauty</option>
                <option value="Cooking">Cooking</option>
                <option value="Traveling">Traveling</option>
              </select>
            </div>
            <div className="create-certificate-data">
              <label htmlFor="price">Price</label>
              <input
                id="price"
                value={price}
                onChange={(event) => setPrice(event.target.value)}
              />
            </div>
            <div className="create-certificate-data">
              <label htmlFor="duration">Duration</label>
              <input
                id="duration"
                value={duration}
                onChange={(event) => setDuration(event.target.value)}
              />
            </div>
          </div>
          <div className="create-certificate-column">
            <div className="create-certificate-data-desc">
              <label htmlFor="description">Description</label>
              <textarea
                id="description"
                value={description}
                onChange={(event) => setDescription(event.target.value)}
                rows="7"
                cols="44"
                required
              ></textarea>
            </div>
            <div className="create-certificate-data">
              <label for="file-input">Image</label>
              <label for="file-input" className="create-certificate-file-label">
                <div className="create-certificate-file-container">
                  <div className="create-certificate-file-icon">
                    <p className="material-icons">Attach file</p>
                  </div>
                  <div className="create-certificate-file-text"></div>
                </div>
                <input
                  type="file"
                  id="file-input"
                  name="file"
                  class="create-certificate-hidden-input"
                  onChange={(event) => setImage(event.target.files[0])}
                />
              </label>
            </div>
            <div className="create-certificate-buttons">
              <input type="submit" value={"Create certificate"} class="create-certificate-submit" onClick={() => createCertificate()}/>
            </div>
          </div>
        </div>
      </main>
    </>
  );
};

export default CreateCertificates;
