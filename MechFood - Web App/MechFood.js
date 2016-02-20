// JavaScript Document
//Code to switch between menu and popular

$(document).ready(function(e) {
	"use strict";
	
	$("#menuButton").click(function(e) {
		console.log("Menu button was clicked");
        $(".content .popularBlock").css("left", "-9000px");
		$(".content .menuBlock").css("display", "inherit");
		$(".content .menuBlock").css("left", "0px");
		$(".content .cartBlock").css("left", "3000px");
		$(".content .cartBlock").css("display", "none");
		$("#popularButton").removeClass("activeButton");
		$("#menuButton").addClass("activeButton");
		$("#cartButton").removeClass("activeButton");
    });    
	
	$("#popularButton").click(function(e) {
		console.log("Popular button was clicked");
        $(".content .menuBlock").css("left", "3000px");
		$(".content .popularBlock").css("left", "0px");
		$(".content .menuBlock").css("display", "none");
		$(".content .cartBlock").css("left", "3000px");
		$(".content .cartBlock").css("display", "none");
		$("#popularButton").addClass("activeButton");
		$("#menuButton").removeClass("activeButton");
		$("#cartButton").removeClass("activeButton");
    });
	
	$("#cartButton").click(function(e) {
		console.log("Cart button was clicked");
        $(".content .menuBlock").css("left", "-9000px");
		$(".content .popularBlock").css("left", "-9000px");
		$(".content .menuBlock").css("display", "none");
		$(".content .cartBlock").css("left", "0px");
		$(".content .cartBlock").css("display", "inherit");
		$("#popularButton").removeClass("activeButton");
		$("#menuButton").removeClass("activeButton");
		$("#cartButton").addClass("activeButton");
    });
	    
});