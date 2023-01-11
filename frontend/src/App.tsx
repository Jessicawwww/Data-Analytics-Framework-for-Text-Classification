import React from 'react';

import './App.css'; // import the css file to enable your styles.
import { Display, DataPlugin, DisplayPlugin } from './display';

/**
 * Define the type of the props field for a React component
 */
interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component<Props, Display> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
     this.state = {
      chartType : "",
      dataSets : [],
      dataPlugins : [],
      displayPlugins : [],
      dataShowDropdown: false,
      displayShowDropdown: false,
      isDisabledDisplay: true,
    };
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */

  async start(){
  const response = await fetch("start");

  const json = await response.json();
  console.log(json);
  this.setState({ dataPlugins: json["dataPlugins"],
                  displayPlugins: json["displayPlugins"]})
}

  chooseDataPlugin(i: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/dataPlugin?i=${i}`)
      const json = await response.json();

      this.setState({ 
                      chartType : json["chartType"],
                      dataSets : json["dataSets"],
                      dataPlugins : json["dataPlugins"],
                      displayPlugins : json["displayPlugins"],
                      dataShowDropdown: false,
                      isDisabledDisplay : false,
                    })
    }
  }

  chooseDisplayPlugin(i: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/displayPlugin?i=${i}`)
      const json = await response.json();

      this.setState({ 
                      chartType : json["chartType"],
                      dataSets : json["dataSets"],
                      dataPlugins : json["dataPlugins"],
                      displayPlugins : json["displayPlugins"],
                      displayShowDropdown: false,
                      isDisabledDisplay : true,
                    })
    }
  }

  createDataPluginsDropdown() {
    if (this.state.dataPlugins.length === 0) {
      return (
        <span>No data plugins loaded</span>
      )
    }
    else {
      return (
        <div>
          {this.state.dataPlugins.map((plugin, index) => this.createDataPlugin(plugin.name, index))}
        </div>
      )
    }
  }

  createDisplayPluginsDropdown() {
    if (this.state.displayPlugins.length === 0) {
      return (
        <span>No display plugins loaded</span>
      )
    }
    else {
      return (
        <div>
          {this.state.displayPlugins.map((plugin, index) => this.createDisplayPlugin(plugin.name, index))}
        </div>
      )
    }
  }

  createDataPlugin(name: string, index: number) {
    return (
      <div key={index}>
        <a href='/' onClick={this.chooseDataPlugin(index)}>{name}</a>
      </div>
    )
  }

  createDisplayPlugin(name: string, index: number) {
    return (
      <div key={index}>
        <a href='/' onClick={this.chooseDisplayPlugin(index)}>{name}</a>
      </div>
    )
  }

  toggleDataDropdown = () => {
    this.setState({dataShowDropdown: !this.state.dataShowDropdown})
  }

  toggleDisplayDropdown = () => {
    this.setState({displayShowDropdown: !this.state.displayShowDropdown})
  }

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */
  componentDidMount(): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.start();
    }
  }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    return (
      <div>
        {/* <div id="game_name">{this.state.name}</div> */}

        {/* {this.createInstructions()} */}

        {/* <div id="footer">{this.state.footer}</div> */}

        <div id="bottombar">
          <button className="dropbtn" onClick={/* get the function, not call the function */this.toggleDataDropdown}>New Classification Data Plugin</button>
          <div id="dropdown-content" className={this.state.dataShowDropdown ? "show" : "hide"}>
            {this.createDataPluginsDropdown()}
          </div>
       </div>
       <div id="bottombar">
          <button className={this.state.isDisabledDisplay ? "dropbtn disabled" : "dropbtn"} onClick={/* get the function, not call the function */this.toggleDisplayDropdown}>New Classification Display Plugin</button>
          <div id="dropdown-content" className={this.state.displayShowDropdown ? "show" : "hide"}>
            {this.createDisplayPluginsDropdown()}
          </div>
       </div>
       <a href="test.html">Click Me To See Graph</a>

      </div>
    );
  }
}

export default App;
