function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.customer_favorite_id = parseInt(values[0].replace(/^"/, "").replace(/"$/, ""));
	obj.customer_id = parseInt(values[1].replace(/^"/, "").replace(/"$/, ""));
	obj.vendor_id = parseInt(values[2].replace(/^"/, "").replace(/"$/, ""));
	obj.category_id = parseInt(values[3].replace(/^"/, "").replace(/"$/, ""));
	obj.item_id = parseInt(values[4].replace(/^"/, "").replace(/"$/, ""));
	obj.source = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[7].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}