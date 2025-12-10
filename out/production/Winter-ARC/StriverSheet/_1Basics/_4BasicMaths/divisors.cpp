class Solution {
public:
    void print_divisors(int n) {
        vector<int> a, b;

        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                a.push_back(i);
                if (i != n / i)
                    b.push_back(n / i);
            }
        }

        for (int x : a)
            cout << x << " ";

        for (int i = b.size() - 1; i >= 0; i--)
            cout << b[i] << " ";
    }
};
