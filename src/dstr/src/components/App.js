//import React, { Component } from 'react';
//import { connect } from 'react-redux';

import React from 'react';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';


const App = () => (
    <div>
        <Header />
        <Body />
        <Footer />
    </div>
)

export default App;



/*
class App extends Component {
    addBasket() {
        console.log('addBasket', this.shawermaInput.value);

        this.props.onAddShawerma(this.shawermaInput.value);
        this.shawermaInput.value = '';
    }

    render() {
        console.log(this.props.testStore);
        return (
            <div>

                <ul>
                    {this.props.testStore.map((shaurma, index) =>
                        <li key={index}>{shaurma}</li>

                    )}
                </ul>

                <Header items={menu} />

                <Ingreedient items={ingredeentlist}/>

                <input type="text" ref={(input) =>
                    {this.shawermaInput = input}} />

                <button onClick={this.addBasket.bind(this)}>Add to basket</button>

            </div>
        );
    }
}


export default connect(
    state => ({
        testStore: state
    }),
    dispatch => ({
        onAddShawerma: (shawermaName) => {
            dispatch({ type: 'ADD_Shawerma', payload: shawermaName});
        }
    })
)(App);
*/

/*import React, { Component } from 'react';
import logo from './logo.svg';


class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
      </div>
    );
  }
}

export default App;
*/
/*
import React, { Component } from 'react';

class App extends Component {
    render() {
        return <div>Hello world!!!!</div>
    }
}

export default App;
*/




















