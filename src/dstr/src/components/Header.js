//import React, { Component } from 'react';
import React from 'react';
import { Link } from 'react-router-dom'

const Header = () => (
    <header>
        <nav>
            <div className="menu">
                <ul>
                    <li><Link to='/'>Main</Link></li>
                    <li><Link to='/order'>Order</Link></li>
                    <li><Link to='/custom'>Custom</Link></li>
                </ul>
            </div>
        </nav>
    </header>
)

export default Header;

/*
class Header extends Component {

    render() {
        return (
            <div>
                {this.props.items.map((item, index) =>
                    <a href={item.link} key={index}>{item.label}</a>
                )}

            </div>
        );
    }
}

export default Header;
*/

