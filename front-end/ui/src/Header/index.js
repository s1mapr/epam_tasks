import React from 'react';
import { useLocalState } from '../util/useLocalStorage';
import './header.css';
import Checkout from '../Checkout';


const Header = () => {
    const [user, setUser] = useLocalState("", "user");

    function logout(){
        localStorage.clear();
        window.location.href="/login";
    }

    let headerContent;

    if(!user){
      headerContent = (
        <header> 
        <div className="header_div dropdown">
            <a href="/certificates">Main Page</a>              
        </div>

        <div className="header_div">
          <form method="get" action="/certificates" className="header_div">
            <input
              type="text"
              id="inputField"
              name="name"
              placeholder="Search by item name"
              className="custom-input"
            />

            <select id="dropdown" name="category" className="custom-select">
              <option value="">All categories</option>
              <option value="Sport">Sport</option>
              <option value="Beauty">Beauty</option>
              <option value="Cooking">Cooking</option>
              <option value="Traveling">Traveling</option>
            </select>
          </form>
        </div>
        <div className="header_div">
          <a href="/login">Login</a>
          <p>|</p>
          <a href="/register">Sign Up</a>
        </div>
      </header>
    );
    } else if (user.role === 'USER') {
        headerContent = (
            <header>
            <div className="header_div dropdown">
                <a href="/certificates">Main Page</a>
            </div>

            <div className="header_div">
            <form method="get" action="/certificates" className="header_div">
                <input
                  type="text"
                  id="inputField"
                  name="name"
                  placeholder="Search by item name"
                  className="custom-input"
                />

                <select id="dropdown" name="category" className="custom-select">
                  <option value="allCategories">All categories</option>
                  <option value="Sport">Sport</option>
                  <option value="Beauty">Beauty</option>
                  <option value="Cooking">Cooking</option>
                  <option value="Traveling">Traveling</option>
                </select>
              </form>
            </div>
            <div className="header_div">

                <p>{user.userName}  |  </p>
                <a href='#' onClick={()=>window.location.href="checkout"}>Checkout</a>
                <a href='#' onClick={logout}>Logout</a>
            </div>
          </header>
        );
        } else if (user.role === 'ADMIN') {
        headerContent = (
            <header>
            <div className="header_div dropdown">
                <a href="/adminPage">Main Page</a>
                <a href="/createCertificate">   New certificate</a>

            </div>

            <div className="header_div">
              
            </div>
            <div className="header_div">
                <p>{user.userName}  |  </p>
                <a href='#' onClick={logout}>Logout</a>
            </div>
          </header>
        );
    }

    return (
        <>
            {headerContent}
        </>
    );
};

export default Header;