#!/usr/bin/env bash

icon_name="$1"

if [[ -z ${icon_name} ]];then
  ls -la $HOME/Downloads/
  echo
  echo "This script requires an argument! (Icon name)"
  exit 1
fi

icon_name="$icon_name.png"
original_origin="$HOME/Downloads/res"

folders=("hdpi" "mdpi" "xhdpi" "xxhdpi" "xxxhdpi")

for folder in "${folders[@]}"; do
  origin="$original_origin/drawable-$folder/$icon_name"
  destination="app/src/main/res/mipmap-$folder/"

  echo "Moving from \"$origin\" to \"$destination\""
  mv ${origin} ${destination}
done

rm -rf "$original_origin"

echo
echo "Done"