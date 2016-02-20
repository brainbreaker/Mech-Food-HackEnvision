// JavaScript Document

//Seller Javascript

var ref = new Firebase("https://mechc.firebaseio.com/");

var products = ref.child("Products");
var carts = ref.child("CartsWeb");
var orders = ref.child("Orders");

function getDataForMenu(snapshot, prevChildKey){
	
		var listItem = document.createElement("li");
		
		var foodObj = document.createElement("div");
		$(foodObj).addClass("dish");
		
		//for div of img
		var foodObj_dishImg = document.createElement("div");
		var actualImg = snapshot.child("imageURL").val();
		$("<img>",{
				src : actualImg
			}).appendTo(foodObj_dishImg);
		$(foodObj_dishImg).addClass("dish_image");
		$(foodObj_dishImg).appendTo(foodObj);
		
		
		//for div of name
		var foodObj_dishName = document.createElement("div");
		var name = snapshot.child("productName").val();
		foodObj_dishName.innerHTML = name;
		$(foodObj_dishName).addClass("dish_name");
		$(foodObj_dishName).appendTo(foodObj);
		
		//for div rate
		var foodObj_dishRate = document.createElement("div");
		var rate = snapshot.child("rate").val();
		foodObj_dishRate.innerHTML = rate;
		$(foodObj_dishRate).addClass("dish_rate");
		$(foodObj_dishRate).appendTo(foodObj);
		
		//for div id
		//for now, we have not included it
		
		//for div cart
		var foodObj_dishCart = document.createElement("div");
		var cartText = 'Add to Cart';
		foodObj_dishCart.innerHTML = cartText;
		$(foodObj_dishCart).addClass("dish_cart");
		foodObj_dishCart.onclick = addToCart;
		$(foodObj_dishCart).appendTo(foodObj);
		
//Now, append foodobject to list item, then list item to the list
		$(foodObj).appendTo(listItem);
		$(listItem).appendTo(".menuBlock ol.dish_list");
}


$(document).ready(function(e) {
    
	
	
	
});