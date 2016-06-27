window.onbeforeunload = confirmExit;
            function confirmExit()
            { 
                if(window.event.clientY < 0){    //&lt je <
               var jsfCommandLink = document.getElementById("f:l");
               jsfCommandLink.click();
                }
               
            }
function autoGrow(textarea) {
    if (textarea.clientHeight < textarea.scrollHeight) {
        textarea.style.height = textarea.scrollHeight + "px";
    }
}

function nestani(button){
    button.style.display = 'none';
}

function invokeCommandLink() {
    
//   if ((window.event.clientY < 0)){
//     var jsfCommandLink = document.getElementById("f:l");
//     jsfCommandLink.click();
     
//    }
  }

function sakrijSve(){
   //document.body.style.display = 'none'; 
   document.write('<style type="text/css">\n\
.centered {padding: 10px 40px 10px 40px;position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);background: white;display: block;margin: 0cm auto;margin-bottom: 0.5cm;  box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);} \n\
h5 {font-family: "RobotoDraft","Roboto",sans-serif; } body{background-color:rgb(208,208,208)}</style>\n\
<div class="centered" style="text-align: center"><h5> Dokument se postavlja na server<br/> Molimo Vas da sačekate...</h5></div>');
    
}
function testAcc(naziv) {
    var x, text;

    // Get the value of the input field with id="numb"
    x = document.getElementById("test").value;

    // If x is Not a Number or less than one or greater than 10
    if (isNaN(x) ) {
        text = "Niste ispunili polje!";
    } else {
        accordion(naziv)
    }
    document.getElementById("demo").innerHTML = text;
}


function w3_open() {
            document.getElementsByClassName("w3-sidenav")[0].style.display = "block";
            document.getElementsByClassName("w3-overlay")[0].style.display = "block";
            }
            
function w3_close() {
            document.getElementsByClassName("w3-sidenav")[0].style.display = "none";
            document.getElementsByClassName("w3-overlay")[0].style.display = "none";
            }
            
window.onscroll = function() {myFunction()};

            
function myFunction() {
            if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
                document.getElementById("myTop").classList.add("w3-card-4");
                document.getElementById("myIntro").classList.add("w3-show-inline-block");
            } else {
                document.getElementById("myIntro").classList.remove("w3-show-inline-block");
                document.getElementById("myTop").classList.remove("w3-card-4");
            }
            }
function openLink(evt, stavkaIme) {
            var i, x, tablinks;
            x = document.getElementsByClassName("stavka");
            for (i = 0; i < x.length; i++) {
               x[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablink");
            for (i = 0; i < x.length; i++) {
               tablinks[i].classList.remove("w3-red");
            }
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.classList.add("w3-red");
            }
            
function GreskaProzor(){   
	var myTextField = document.getElementById("Greska");
	if(myTextField.value != ""){		
             document.getElementById("modal").style.display="block";
         }
}
function w3_openMeni() {
  document.getElementById("main").style.marginLeft = "25%";
  document.getElementsByClassName("w3-sidenav")[0].style.width = "25%";
  document.getElementsByClassName("w3-sidenav")[0].style.display = "block";
  document.getElementsByClassName("w3-opennav")[0].style.display = 'none';
}
function w3_closeMeni() {
  document.getElementById("main").style.marginLeft = "0%";
  document.getElementsByClassName("w3-sidenav")[0].style.display = "none";
  document.getElementsByClassName("w3-opennav")[0].style.display = "inline-block";
}

function printPage() {
    var elem = document.getElementById("footer");
    
    var content = document.getElementById("main").innerHTML;
    var i, j, c = document.getElementById("main").cloneNode(true);
    for (i = 0; i < c.childNodes.length; i++) {
        //bio u if-u : && c.childNodes[i].getAttribute("id") == "mainLeaderboard"
        if (c.childNodes[i].nodeType == 1 ) {
            c.removeChild(c.childNodes[i]);
            content = c.innerHTML;
            break;
        }
    }
    var head = document.getElementsByTagName("head")[0].innerHTML;
    
    

    var myWindow=window.open('','','');
    myWindow.document.write("<html><head>"+head+
            "<style>\n\
            body{width: 21 cm; min-height: 29.7cm;pading 15px} \n\
            page {background :white;display:block; margin: 0 auto; margin-bottom: 0; box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);} page[size='A4']{width:21cm;height: 29.7cm;}\n\
            @media print {\n\
            .printbtn {display:none;}\n\
            body{ background-color:#FFFFFF; background-image:none; color:#000000; width:100%;#print{font-size: 10px;} \n\
            }}\n\
            </style>\n\
            </head>\n\
            <body>\n\
            <button class='printbtn' onclick='window.print()'>Print Page</button><br><page size='A4'> "
            +content+"</page></body></html>");
   /* window.alert("<html><head>"+head+
            "<style>body{padding:15px;} @media print {.printbtn {display:none;}body{ background-color:#FFFFFF; background-image:none; color:#000000; width:100%;#print{font-size: 10px;} }}</style></head><body><button class='printbtn' onclick='window.print()'>Print Page</button><br><br>"
            +content+"</body></html>");
     
     *
     *page {
  background: white;
  display: block;
  margin: 0 auto;
  margin-bottom: 0.5cm;
  box-shadow: 0 0 0.5cm rgba(0,0,0,0.5);
}
page[size="A4"] {  
  width: 21cm;
  height: 29.7cm; 
}
     **/
}
//edituje formu na event tastature
//<h:form id="YourForm" onkeypress ="if (event.keyCode == 84) {saveForm(); return false;}; return true;">
//        <h:inputHidden id="hiddenSave" action="YourBean.SaveAction"/>
//</h:form>
function saveForm(){
     if (event.keyCode == 84){
          document.getElementById('YourForm:hiddenSave').click();
          return false;
     }
     return true;
}
function printPage1() {
    var elem = document.getElementById("footer");
    
    var content = document.getElementById("main").innerHTML;
    var i, j, c = document.getElementById("main").cloneNode(true);
    for (i = 0; i < c.childNodes.length; i++) {
        //bio u if-u : && c.childNodes[i].getAttribute("id") == "mainLeaderboard"
        if (c.childNodes[i].nodeType == 1 ) {
            c.removeChild(c.childNodes[i]);
            content = c.innerHTML;
            break;
        }
    }
    var head = document.getElementsByTagName("head")[0].innerHTML;
    
    

    var myWindow=window.open('','','');
    myWindow.document.write("<html><head>"+head+
            "<style>\n\
                body{padding:15px;width: 210mm;height: 297mm;} \n\
                @media print {.printbtn {display:none;}\n\
                body{ background-color:#FFFFFF; \n\
                background-image:none; \n\
                color:#000000; \n\
                width:100%;\n\
                #print{font-size: 10px;} \n\
                }}\n\
                </style>\n\
                </head>\n\
                <body><button class='printbtn' onclick='window.print()'>Print Page</button><br><br>"
                +content+"\
                </body>\n\
                </html>");
   
}

function err(){
    document.write('<header class="w3-container w3-red"><h2>Nedozvoljen pristup!!!</h2></header><div class="w3-center"><br/><img value="../resources/img/tfbgrb.png"  alt="Grb" style="width: 35%"  class="w3-circle w3-margin-top"/></div><div class="w3-container"><div class="w3-section"><p class="w3-center w3-medium">Niste logovani na sistem. Morate se logovati!!!<br/></p></div></div> <footer class="w3-container w3-red w3-right-align"><p>Autor Amel Džanić</p></footer></div></div></h:form>');
    document.getElementById("modal").style.display="block";
}

function accordion(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }
}


