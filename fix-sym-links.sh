set -e

project="debugging-native"

pushd target/*-native-image-source-jar/reports

rm -f call_tree_vm.csv
ln -s call_tree_vm_${project}-* call_tree_vm.csv

rm -f call_tree_direct_edges.csv
ln -s call_tree_direct_edges_${project}-* call_tree_direct_edges.csv

rm -f call_tree_entry_points.csv
ln -s call_tree_entry_points_${project}-* call_tree_entry_points.csv

rm -f call_tree_methods.csv
ln -s call_tree_methods_${project}-* call_tree_methods.csv

rm -f call_tree_virtual_edges.csv
ln -s call_tree_virtual_edges_${project}-* call_tree_virtual_edges.csv

rm -f call_tree_virtual_methods.csv
ln -s call_tree_virtual_methods_${project}-* call_tree_virtual_methods.csv

rm -f call_tree_override_by_edges.csv
ln -s call_tree_override_by_edges_${project}-* call_tree_override_by_edges.csv

popd
