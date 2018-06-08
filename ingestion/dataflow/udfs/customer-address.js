function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.address_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.customer_id = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.address_line_1 = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.address_line_2 = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.city = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.state = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.postal_code = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.country = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[9].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}