function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.item_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.vendor_id = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.division_id = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.department_id = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.classification_id = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.sub_classification_id = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.style_id = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.sku = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.color = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.size = values[9].replace(/^"/, "").replace(/"$/, "");
	obj.short_description = values[10].replace(/^"/, "").replace(/"$/, "");
	obj.long_description = values[11].replace(/^"/, "").replace(/"$/, "");
	obj.image_url = values[12].replace(/^"/, "").replace(/"$/, "");
	obj.current_price = values[13].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[14].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[15].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}