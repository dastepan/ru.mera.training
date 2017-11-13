import React from 'react'
import { Switch, Route } from 'react-router-dom'
import Main from './Main'
import Order from './Order'
import Custom from './Custom'



const Body = () => (
    <main>
        <Switch>
            <Route exact path='/' component={Main}/>
            <Route path='/order' component={Order}/>
            <Route path='/custom' component={Custom}/>
        </Switch>
    </main>

)

export default Body




// The Main component renders one of the three provided
// Routes (provided that one matches). Both the /roster
// and /schedule routes will match any pathname that starts
// with /roster or /schedule. The / route will only match
// when the pathname is exactly the string "/"