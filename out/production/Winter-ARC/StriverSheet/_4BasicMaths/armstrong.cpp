#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    bool armstrongNumber(int n) {
        int sum = 0, x = n, y = n, r = 0;

        // Count digits
        while (n > 0) {
            r++;
            n = n / 10;
        }

        // Compute sum of digits^r
        while (x > 0) {
            sum += pow(x % 10, r);
            x = x / 10;
        }

        return sum == y;
    }
};

int main() {
    int n;
    cin >> n;

    Solution ob;

    if (ob.armstrongNumber(n))
        cout << "Yes";
    else
        cout << "No";

    return 0;
}
