function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.customer_relationship_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.source_customer_id = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.target_customer_id = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.relationship_type = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[5].replace(/^"/, "").replace(/"$/, "");
	
	var jsonString = JSON.stringify(obj);

	return jsonString;
}