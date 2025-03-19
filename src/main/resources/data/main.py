import json

tsv_file = "cities_canada-usa.tsv"
json_file = "bulk_geonames.json"

index_name = "geonames_v2"
bulk_data = []

with open(tsv_file, "r", encoding="utf-8") as file:
    next(file)
    for line in file:
        fields = line.strip().split("\t")
        if len(fields) < 19:
            continue

        doc_id = fields[0]
        name = fields[1]
        asciiname = fields[2]
        alternatenames = fields[3].split(",") if fields[3] else []
        latitude = float(fields[4])
        longitude = float(fields[5])
        country_code = fields[8]
        admin1_code = fields[10]
        admin2_code = fields[11]
        population = int(fields[14]) if fields[14].isdigit() else 0
        timezone = fields[17]

        bulk_data.append(json.dumps({ "index": { "_index": index_name, "_id": doc_id } }))
        bulk_data.append(json.dumps({
            "name": name,
            "asciiname": asciiname,
            "alternatenames": alternatenames,
            "latitude": latitude,
            "longitude": longitude,
            "location": { "lat": latitude, "lon": longitude },
            "country_code": country_code,
            "admin1_code": admin1_code,
            "admin2_code": admin2_code,
            "population": population,
            "timezone": timezone
        }))

with open(json_file, "w", encoding="utf-8") as f:
    f.write("\n".join(bulk_data) + "\n")

print(f"✅ Data berhasil dikonversi dan disimpan di {json_file}")
