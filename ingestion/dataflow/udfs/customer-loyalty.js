function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.customer_loyalty_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_number = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_join_date = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_lifetime_points = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_available_points = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_level = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_level_description = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.loyalty_level_expiration = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[9].replace(/^"/, "").replace(/"$/, "");
	obj.customer_id = values[10].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}