//JS to handle Firebase

var ref = new Firebase("https://mechc.firebaseio.com/");

var products = ref.child("Products");
var carts = ref.child("CartsWeb");
var orders = ref.child("Orders");

var myCart;
var myActualCart;

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

function getDataForCart(snapshot, prevChildKey){
		
		"use strict";
		
		var listItem = document.createElement("li");
		
		var foodObj = document.createElement("div");
		$(foodObj).addClass("cart_item");

		//for div of cart_total
		var foodObj_total = document.createElement("div");
		var rate = snapshot.child("foodRate").val();
		var qty = snapshot.child("quantity").val();
		foodObj_total.innerHTML = rate * qty;
		$(foodObj_total).addClass("cart_item_total");
		$(foodObj_total).appendTo(foodObj);
		
		var temp_total_price = $(".checkout_total").html();
		temp_total_price = parseInt(temp_total_price);
		temp_total_price = temp_total_price + rate*qty;
		$(".checkout_total").html(temp_total_price);
		
		//for div of cart_name
		var foodObj_dishName = document.createElement("div");
		var name = snapshot.child("foodName").val();
		foodObj_dishName.innerHTML = name;
		$(foodObj_dishName).addClass("cart_item_name");
		$(foodObj_dishName).appendTo(foodObj);
		
		//for div of cart_qty
		var foodObj_dishQty = document.createElement("div");
		var qtySpan = document.createElement("span");
		qtySpan.innerHTML = qty;
		$(qtySpan).appendTo(foodObj_dishQty);
		$(foodObj_dishQty).addClass("cart_item_quantity");
		
		var button_plus = document.createElement("button");
		button_plus.innerHTML = "+";
		$(button_plus).addClass("plus");
		$(button_plus).click(plus_click);
		
		var button_minus = document.createElement("button");
		button_minus.innerHTML = "-";
		$(button_minus).addClass("minus");
		$(button_minus).click(minus_click);
		
		$(button_minus).prependTo(foodObj_dishQty);
		$(button_plus).appendTo(foodObj_dishQty);
				
		$(foodObj_dishQty).appendTo(foodObj);
		
		//for div cart_rate
		var foodObj_dishRate = document.createElement("div");
		foodObj_dishRate.innerHTML = rate;
		$(foodObj_dishRate).addClass("cart_item_rate");
		$(foodObj_dishRate).appendTo(foodObj);
		
		//for div cart_rate
		var foodObj_dishRemove = document.createElement("div");
		foodObj_dishRemove.innerHTML = "X";
		$(foodObj_dishRemove).addClass("cart_remove");
		$(foodObj_dishRemove).click(removeFromCart);
		$(foodObj_dishRemove).appendTo(foodObj);
		
		//Storing Key of cart item
		var itemKey = document.createElement("div");
		itemKey.innerHTML = snapshot.key();
		//console.log(itemKey.innerHTML);
		$(itemKey).addClass("dish_id");
		$(itemKey).appendTo(foodObj);
		
		//Now, append foodobject to list item, then list item to the list
		$(foodObj).appendTo(listItem);
		$(listItem).appendTo(".cartBlock ol.dish_list");
		
		console.log("Cart item displayed");
}


//For Popular
function getDataForPopular(snapshot, prevChildKey){
		
		"use strict";
		
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
		foodObj_dishCart.onclick = addToCart;
		$(foodObj_dishCart).addClass("dish_cart");
		$(foodObj_dishCart).appendTo(foodObj);
		
		//Now, append foodobject to list item, then list item to the list
		$(foodObj).appendTo(listItem);
		$(listItem).prependTo(".popularBlock ol.dish_list");
}



function createCart(){
	"use strict";
	console.log("Cart Created");
	myCart = ref.child("CartsWeb").push();
}

function plus_click(e) {
	
//		"use strict";
		console.log("Plus");
		
		var total_price  = parseInt($(".checkout_total").html());
		
        var cartQtyDiv = this.parentNode;
		//alert(cartQtyDiv.className);
		var cartItem = this.parentNode.parentNode;
		//alert(cartItem.className);
		var temp_qty = $(cartItem).children(".cart_item_quantity").children("span").html();
		
		//console.log(temp_qty);
		temp_qty++;
		$(cartItem).children(".cart_item_quantity").children("span").html(temp_qty);
		$(cartItem).children(".cart_item_total").html(
							$(cartItem).children(".cart_item_rate").html() * temp_qty
						);
		
		$(".checkout_total").html(total_price + parseInt($(cartItem).children(".cart_item_rate").html()));
						
		var keyOfItem = $(cartItem).children(".dish_id").html();
		myCart.child(keyOfItem).update(
			{"quantity" : temp_qty}
		);
}


function minus_click(e) {
	
//		"use strict";
		console.log("Minus");
		var total_price  = parseInt($(".checkout_total").html());
        var cartQtyDiv = this.parentNode;
		//alert(cartQtyDiv.className);
		var cartItem = this.parentNode.parentNode;
		//alert(cartItem.className);
		var temp_qty = $(cartItem).children(".cart_item_quantity").children("span").html();
		//console.log(temp_qty);
		temp_qty--;
		
		if(temp_qty===-1){
			temp_qty = 0;
		}
		else{
			$(".checkout_total").html(total_price - parseInt($(cartItem).children(".cart_item_rate").html()));
		}
		
		$(cartItem).children(".cart_item_quantity").children("span").html(temp_qty);
		$(cartItem).children(".cart_item_total").html(
							$(cartItem).children(".cart_item_rate").html() * temp_qty
						);				
						
		var keyOfItem = $(cartItem).children(".dish_id").html();
		
		myCart.child(keyOfItem).update(
			{"quantity" : temp_qty}
		);
}

function removeFromCart(){
	var total_price  = parseInt($(".checkout_total").html());
	var cartItem = this.parentNode;
	var keyOfItem = $(cartItem).children(".dish_id").html();
	
	myCart.child(keyOfItem).remove();
	
	var parentOfCartItem = cartItem.parentNode;
	$(".checkout_total").html(total_price - parseInt($(cartItem).children(".cart_item_total").html()));
	parentOfCartItem.removeChild(cartItem);
	
}

function addToCart(){
	"use strict";
	
	var foodObj = this.parentNode;
	var foodName = $(foodObj).children(".dish_name");
	var foodRate = $(foodObj).children(".dish_rate");
	var qty = 1;
	
	var obj = {
				"foodName" : foodName.html(),
				"foodRate" : foodRate.html(),
				"quantity" : qty
	};
	

	var itemList = carts;
	
/*
	Redundency couldn't be resolved.....will work on it later
	
	itemList.child("/foodName").equalTo(foodName).on("child_added", function(snapshot){
			if(snapshot===null){
				console.log("Null Null");
			}
			else{
				console.log("Added!");
			}
		});
*/
	myCart.push(obj);
	
	var name = myCart.key();
	
	console.log("addToCart Clicked");
	console.log(name);	
}

$(document).ready(function(e) {
	
	"use strict";
	
	console.log("HEllo");
	
	createCart();	
	products.on("child_added", getDataForMenu);
	products.orderByChild("count").limitToLast(3).on("child_added", getDataForPopular);
	myCart.on("child_added", getDataForCart);
	
	$(".checkout_submit").click(function(e) {
		
		
		var totalAmt = parseInt($(".checkout_total").html());
		
        var temp = document.getElementById("table");
		
		if((temp.value)===('') || (temp.value)===(' ')){
			alert("Please Enter a Table Number");
		}
		else if(parseInt($(".checkout_total").html())===0){
			alert("Please Add Something to Cart");
		}
		else{
		var finalOrder = {
				"status" : "accepted",
				"tableNo" : temp.value,
				"totalAmt" : totalAmt,
				"cartDetails" : myCart.key()
			};
		
		orders.push(finalOrder);
		alert("Your order has been placed! Thank You! The page will now reload");
		location.reload(true);
		}
    });
	    
});