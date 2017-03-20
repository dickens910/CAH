
	$(document).ready(function(){
           var mydir = getCurrentDirectory();   
		$.ajaxSetup({beforeSend: function(xhr){
			   if (xhr.overrideMimeType)
				  {
				    xhr.overrideMimeType("application/json");
				  }
				}
			});//to avoid error message on firefox
		getArrayOfWhiteCards(mydir); //put all White cards in an array to manipulate them (deck)
		getArrayOfBlackCards(mydir); //put all Black cards in an array to manipulate them (deck)


	});
	//functions
		var cardDeck =[]; //white card Deck
		var blackcardDeck = []
	 	function getArrayOfWhiteCards(mydir){
			$.getJSON(mydir+"/whiteCards.json", function(dataObj){
				$.each(dataObj, function(i, whitecard){
					for (var  f = 0 ; f < whitecard.length; f++)
					{
						var card = whitecard.slice(f,f+1);
						cardDeck[f] = card;	
					}
					return false
				});
			});
		}//end of getArrayOfWhiteCards
		function getArrayOfBlackCards(mydir){
			$.getJSON(mydir+"/blackCards.json", function(dataObj){
				$.each(dataObj, function(i, blackcard){
					for (var  f = 0 ; f < blackcard.length; f++)
					{
						var card = blackcard.slice(f,f+1);		//separates the big array 
						var condition = false ;
						while (!condition ){			//check if coma to add _______ for empty word
							var str = card.toString();
							var str2 = card.toString();
							var coma = str.includes(",.") ; // cherche des "," dans le texte
								if (coma){
									card = str.replace(",."," , _________ .");
								}
								else condition = true;
							}	
						blackcardDeck[f] = card;	
					}
					return false
				});
			});
		}	//end of getArrayOfBlackCards
		function giveCards(){
			for (var  f = 0 ; f < 10; f++)
					{
						var rnd = Math.floor((Math.random() * cardDeck.length+1) + 0);
						$("#wCard").append("<div ><div>"+ cardDeck[rnd] +"</div></div>"  );
						$("#wCard > div").addClass("whiteCard");
						$(".whiteCard > div").addClass("inner");
						cardDeck.splice(rnd,1);
					}
					choosedCard();
		} //end o fgiveCards
		function giveBlackCard(){
			for (var  f = 0 ; f < 1; f++)
					{
						var rnd = Math.floor((Math.random() * blackcardDeck.length+1) + 0);
						$("#bCard").append("<div><div>"+ blackcardDeck[rnd] +"</div></div>"  );
						$("#bCard > div").addClass("blackCard");
						$(".blackCard > div").addClass("inner");
						blackcardDeck.splice(rnd,1); //remove that card from deck adn delete that position
					}
		} //end of giveBlaccards
		function choosedCard(){
			$(".whiteCard").on("click", function(){
				alert("The paragraph was clicked.");
			});	
		}//end of chooseCard
		function getCurrentDirectory()
                 {
                    var scripts= document.getElementsByTagName('script');
                    var path= scripts[scripts.length-1].src.split('?')[0];      // remove any ?query
                    var mydir= path.split('/').slice(0, -1).join('/')+'/';  // remove last filename part of path
                    return mydir;
                }		