function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.store_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.store_num = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.store_name = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.store_address_line_1 = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.store_address_line_2 = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.store_city = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.store_state = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.store_postal_code = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.store_country = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.latitude = values[9].replace(/^"/, "").replace(/"$/, "");
	obj.longitude = values[10].replace(/^"/, "").replace(/"$/, "");
	obj.store_phone_number = values[11].replace(/^"/, "").replace(/"$/, "");
	obj.open_date = values[12].replace(/^"/, "").replace(/"$/, "");
	obj.close_date = values[13].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[14].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[15].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}