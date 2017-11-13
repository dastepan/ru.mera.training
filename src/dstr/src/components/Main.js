import React from 'react'


// ДАННЫЕ которые получили от backend
const nameList = [
    {
        name: 'Shawerma',
    }
]

const ingredeentList = [
    {
        label: '800g Pork'
    },
    {
        label: '100g Onion'
    }
]

const nameProdocts = 'Shawerma'


const Main = () => (
    <div>
        <div>
            <div className="order"> </div>
        </div>
        <div className="wrapper">
            <div className="card">
                <div className="foto"><p>FOTO</p></div>
                <h4>{nameProdocts}</h4>
                <ul>
                    <li>800g Porc</li>
                    <li>100g Onion</li>
                </ul>
                <br/><br/>
                <button type="submit" value={nameProdocts}>Add to basket</button>
            </div>
            <div className="card"> </div>
            <div className="card"> </div>
            <div className="card"> </div>
        </div>
    </div>
)

export default Main


