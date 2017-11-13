import React from 'react';
import { Link } from 'react-router-dom'

const Footer = () => (
    <header>
        <nav>
            <div className="footer">
                <ul>
                    <li><Link to='#'>О нас</Link></li>
                    <li><Link to='#'>О компании</Link></li>
                    <li><Link to='#'>История</Link></li>
                </ul>
                <ul>
                    <li><Link to='#'>Наши офисы</Link></li>
                    <li><Link to='#'>Новости</Link></li>
                    <li><Link to='#'>Контакты</Link></li>
                </ul>
                <ul>
                    <li><Link to='#'>Вакансии</Link></li>
                    <li><Link to='#'>Обратная связь</Link></li>
                    <li><Link to='#'>Схема проезда</Link></li>
                </ul>
            </div>
        </nav>
    </header>
)

export default Footer;