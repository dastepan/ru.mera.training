import React from 'react'
//import { Switch, Route } from 'react-router-dom'

const Share = 'Share'
const To_order = 'To_order'

const Order = () => (
    <div>
        <div className="wrapper">
            <div>
                <div className="orderBlock">
                    <div className="orderBlockInner">
                        <p>Ivan Ivanov</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>Shaverma</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>Size</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>Ingredients</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>2</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>3$</p>
                    </div>
                    <div className="orderBlockInner">
                        <p>X</p>
                    </div>
                </div>
                <div className="orderBlock"></div>
                <div className="orderBlock"></div>
                <div className="orderBlock"></div>
            </div>
        </div>

        <div className="orderButtonBlock">
            <button type="submit" value={Share}>Share</button>
            <button type="submit" value={To_order}>To order</button>
        </div>

    </div>

)

export default Order


// import FullRoster from './FullRoster'
// import Player from './Player'
//
// // The Roster component matches one of two different routes
// // depending on the full pathname
// const Roster = () => (
//     <Switch>
//         <Route exact path='/roster' component={FullRoster}/>
//         <Route path='/roster/:number' component={Player}/>
//     </Switch>
// )
//
//
// export default Roster