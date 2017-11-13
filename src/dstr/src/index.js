import './index.css';

import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
//import { Router, Route } from 'react-router'
//import { BrowserRouter, Link, Route } from 'react-router-dom'
import { BrowserRouter } from 'react-router-dom'

import App from './components/App';


const nameShawerma = [
    'Shawerma',
];

function namelist(state = nameShawerma, action) {
    if (action.type === 'ADD_Shawerma') {
        return [
            ...state,
            action.payload
        ];
    }
    return state;
}


const store = createStore(namelist);    //хранилище всех наших данных приложения

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);

// const history = syncHistoryWithStore(BrowserRouter, store)
// ReactDOM.render(
//     <Provider store={store}>
//         <Router history={history}>
//
//             <Route path="/" component={App} />
//
//         </Router>
//     </Provider>,
//     document.getElementById('root')
// );



//registerServiceWorker();


// import { createStore } from 'redux';
//
// function orderlist(state = [], action) {
//     if (action.type === 'ADD_Shawerma') {
//         return [
//             ...state,
//             action.payload
//         ];
//     }
//     return state;
// }
//
// const store = createStore(orderlist);    //хранилище всех наших данных приложения
// const addBasketBtn = document.querySelectorAll('.addBasket')[0];
// const shawermaInput = document.querySelectorAll('.shawermaInput')[0];
// const list = document.querySelectorAll('.list')[0];
//
// store.subscribe(() => {                 // подписываемся на изменения в store
//     list.innerHTML = '';
//     shawermaInput.value = '';
//     store.getState().forEach(shawerma => {
//        const li = document.createElement('li');
//        li.textContent = shawerma;
//        list.appendChild(li);
//     });
// })
//
// addBasketBtn.addEventListener('click', () => {
//     const shawermaName = shawermaInput.value;
//     store.dispatch({ type: 'ADD_Shawerma', payload: shawermaName}); // поменять значение в store
// });





