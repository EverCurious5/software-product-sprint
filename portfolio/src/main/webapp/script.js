// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
/*function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}*/

/**
 * Fetches data from the server and adds it to the DOM.
 */
/*function getData() {
  console.log('Fetching data');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/data');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addDataToDom().
 
function handleResponse(response) {
  console.log('Handling the response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addDataToDom() function.
  textPromise.then(addDataToDom);
}*/

/** Adds Data to the DOM. 
function addDataToDom(Data) {
  console.log('Adding Data to dom: ' + Data);

  const DataContainer = document.getElementById('Data-container');
  DataContainer.innerText = Data;
}*/

/*function getData(){
    fetch('/data').then(response => response.json()).then((data) => {
       // document.getElementById('msg').innerText = data;
       // console.log(data[0]);

        const ob=document.getElementById('msg');
        ob.innerHTML='';
        for (var i = 0; i < data.length; i++) {
            ob.appendChild(createListElement(data[i]));
        }     

        
    });
}

/** Creates an <li> element containing text.
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
} */


/** Fetches tasks from the server and adds them to the DOM. */
//window.customElements.define('comment-element', class extends HTMLElement {'p'});
async function getComments(){
  const response = await fetch('/data');
  const comments = await response.json();
  container = document.getElementById('comments-container'); 
  comments.forEach((comment)=>{
      container.appendChild(createListElement(comment));
  });
  
}

function createListElement(text) {
  const element = document.createElement('li');

    element.className = 'text';

  const titleElement = document.createElement('span');
  titleElement.innerText = text.title;

  element.appendChild(titleElement);

  element.innerText =  text.title ;
  console.log(text.title);
  return element;
}

async function allowornot(){
    await getComments();
    const response = await fetch('/login');
    const userResponse = await response.json();
    const commentTextArea = document.getElementById('content1');
    const logoutuser = document.getElementById('userlogout');
    const loginUrl = document.getElementById('login-url');
    const loginuser = document.getElementById('userlogin');
    const logoutUrl = document.getElementById('logout-url');
    console.log(userResponse);
    if (userResponse.LoggedIn) {
        console.log("Logged in");
        loginuser.hidden = true;
        logoutUrl.href = userResponse.userURL;       
    } 
    else{
        console.log("Logged out");
        commentTextArea.hidden = true;
        logoutuser.hidden = true;
        loginUrl.href = userResponse.userURL;
     }
}




